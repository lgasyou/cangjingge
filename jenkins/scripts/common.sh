#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

readonly MVN_OPTS="-DskipTests -Pproduct"

readonly SUBMODULES=(authorization eureka-server fiction fiction-review hystrix-dashboard user zuul-gateway)
readonly BASE_DIR=$(pwd)
readonly IMAGE_PREFIX="lgasyou/cangjingge-"

tag=""

function build::package-and-build-image() {
  mvn "${MVN_OPTS}" clean package spring-boot:repackage dockerfile:build
}

function build::install-common-module() {
    cd common
    mvn "${MVN_OPTS}" clean install
    cd "${BASE_DIR}"
}

function build::build-all-image() {
  echo "Installing module common..."
  build::install-common-module

  for module in ${SUBMODULES[*]}; do
    echo "Building image of module ${module}..."
    cd "${module}"
    build::package-and-build-image
    cd "${BASE_DIR}"
  done
}

function deliver::deliver() {
  local image_name=$1
  local tag=$2
   docker push "${image_name}:${tag}"
}

function deliver::deliver-all() {
  for module in ${SUBMODULES[*]}; do
    cd "${module}"
    local image_name="${IMAGE_PREFIX}${module}"
    util::get-project-version
    echo "Pushing image ${image_name}:${tag}"
    deliver::deliver "${image_name}" "${tag:-latest}"
    cd "${BASE_DIR}"
  done
}

function util::get-project-version() {
  tag=$(mvn help:evaluate -Dexpression=project.version | grep "^[^\[]")
}
