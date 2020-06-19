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

function build::package-and-build-image() {
  mvn "${MVN_OPTS}" clean package spring-boot:repackage dockerfile:build
}

function build::install-modules() {
  mvn "${MVN_OPTS}" clean install
}

function build::build-all-image() {
  build::install-modules

  for module in ${SUBMODULES[*]}; do
    echo "Building image of module ${module}..."
    cd "${module}"
    build::package-and-build-image
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

function util::get-project-version() {
  tag=$(mvn help:evaluate -Dexpression=project.version | grep "^[^\[]")
}
