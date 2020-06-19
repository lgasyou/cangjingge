#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

readonly MVN_OPTS="-DskipTests -Pproduct"

readonly SUBMODULES=(
  cangjingge-authorization
  cangjingge-eureka-server
  cangjingge-fiction
  cangjingge-fiction-review
  cangjingge-hystrix-dashboard
  cangjingge-user
  cangjingge-zuul-gateway
)
readonly BASE_DIR=$(pwd)
readonly IMAGE_PREFIX="lgasyou"

tag=""
full_image_name=""
newest_image_existed=""

function build::package-and-build-image() {
  mvn "${MVN_OPTS}" clean package spring-boot:repackage dockerfile:build
}

function build::install-modules() {
  mvn "${MVN_OPTS}" clean install
}

function build::build-all-image() {
  build::install-modules

  for module in ${SUBMODULES[*]}; do
    cd "${module}"

    # Try to pull image first
    util::get-project-version
    full_image_name="${IMAGE_PREFIX}/${module}:${tag}"
    util::try-pull-image

    # Only if image not exists we start building it.
    util::has-newest-image-existed "${IMAGE_PREFIX}/${module}"
    if [[ -z ${newest_image_existed} ]]; then
      echo "Building image of module ${module}..."
      build::package-and-build-image
    else
      echo "Skipped module ${module}:${tag} because it has already been built."
    fi
    cd "${BASE_DIR}"
  done
}

function deliver::deliver() {
   docker push "${full_image_name}"
}

function deliver::deliver-all() {
  for module in ${SUBMODULES[*]}; do
    cd "${module}"
    util::get-project-version
    full_image_name="${IMAGE_PREFIX}/${module}:${tag}"
    echo "Pushing image ${full_image_name}"
    deliver::deliver
    cd "${BASE_DIR}"
  done
}

function util::try-pull-image() {
  docker pull "${full_image_name}" || true
}

function util::has-newest-image-existed() {
  local repository=$1
  image_exists=$(docker images "${repository}" | awk '{print $2}')
  if [[ ${image_exists} == 'TAG' ]]; then
    newest_image_existed=""
  else
    newest_image_existed=$(echo "${image_exists}" | grep -v 'TAG' | grep "${tag}")
  fi
}

function util::get-project-version() {
  tag=$(mvn help:evaluate -Dexpression=project.version | grep "^[^\[]")
}
