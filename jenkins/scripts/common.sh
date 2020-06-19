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
readonly DOCKER_CE_VERSION=18.06.3-ce

tag=""
full_image_name=""
newest_image_existed=""

function init::install_docker_ce() {
  curl -fsSLO "https://download.docker.com/linux/static/stable/x86_64/docker-${DOCKER_CE_VERSION}".tgz
  tar xzvf docker-"${DOCKER_CE_VERSION}".tgz --strip 1 -C /usr/local/bin docker/docker
  rm docker-"${DOCKER_CE_VERSION}".tgz
}

function build::package_and_build_image() {
  mvn "${MVN_OPTS}" clean package spring-boot:repackage dockerfile:build
}

function build::install_modules() {
  mvn "${MVN_OPTS}" clean install
}

function build::build_all_image() {
  build::install_modules

  for module in ${SUBMODULES[*]}; do
    cd "${module}"

    # Try to pull image first
    util::get_project_version
    full_image_name="${IMAGE_PREFIX}/${module}:${tag}"
    util::try_pull_image

    # Only if image not exists should we start building it.
    util::has_newest_image_existed "${IMAGE_PREFIX}/${module}"
    if [[ ${newest_image_existed} == 0 ]]; then
      echo "Building image of module ${module}..."
      build::package_and_build_image
    else
      echo "Skipped module ${module}:${tag} because it is up-to-date."
    fi
    cd "${BASE_DIR}"
  done
}

function deliver::deliver() {
   docker push "${full_image_name}"
}

function deliver::deliver_all() {
  for module in ${SUBMODULES[*]}; do
    cd "${module}"
    util::get_project_version
    full_image_name="${IMAGE_PREFIX}/${module}:${tag}"
    echo "Pushing image ${full_image_name}"
    deliver::deliver
    cd "${BASE_DIR}"
  done
}

function util::try_pull_image() {
  docker pull "${full_image_name}" || true
}

function util::has_newest_image_existed() {
  local repository=$1
  image_exists=$(docker images "${repository}:${tag}" | awk '{print $2}')
  if [[ ${image_exists} == 'TAG' ]]; then
    newest_image_existed=0
  else
    newest_image_existed=1
  fi
}

function util::get_project_version() {
  tag=$(mvn help:evaluate -Dexpression=project.version | grep "^[^\[]")
}
