#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

source "jenkins/scripts/common.sh"

deliver::deliver_all
