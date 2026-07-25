#!/bin/bash

# Simple script to calculate the next version based on commit messages
# Usage: ./versioning.sh [major|minor|patch]

current_version=$(git describe --tags --abbrev=0 2>/dev/null || echo "v0.0.0")
version_parts=(${current_version//v/})
version_parts=(${version_parts//./ })

major=${version_parts[0]}
minor=${version_parts[1]}
patch=${version_parts[2]}

case "$1" in
    major)
        major=$((major + 1))
        minor=0
        patch=0
        ;;
    minor)
        minor=$((minor + 1))
        patch=0
        ;;
    patch|*)
        patch=$((patch + 1))
        ;;
esac

new_version="v$major.$minor.$patch"
echo "Bumping version from $current_version to $new_version"
echo "$new_version"
