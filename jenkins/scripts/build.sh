#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

source "jenkins/scripts/common.sh"

build::build_all_image
