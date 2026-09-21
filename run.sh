#!/bin/bash
# College ERP - Build and Run Script
# Usage: ./run.sh [build|run|clean]

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
SRC_DIR="$PROJECT_DIR/src"
BUILD_DIR="$PROJECT_DIR/build/classes"
MAIN_CLASS="attendancemgt.AttendanceMgt"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

build() {
    echo -e "${YELLOW}Building College ERP...${NC}"

    # Clean build directory
    rm -rf "$BUILD_DIR"
    mkdir -p "$BUILD_DIR/attendancemgt/resources"

    # Copy resources (images, etc.)
    cp "$SRC_DIR/attendancemgt/resources/images.png" "$BUILD_DIR/attendancemgt/resources/"

    # Compile all Java files
    javac -d "$BUILD_DIR" "$SRC_DIR/attendancemgt/"*.java 2>&1

    if [ $? -eq 0 ]; then
        CLASS_COUNT=$(ls "$BUILD_DIR/attendancemgt/"*.class 2>/dev/null | wc -l)
        echo -e "${GREEN}✓ Build successful!${NC} ($CLASS_COUNT class files)"
        echo -e "${GREEN}  Resources: $(ls "$BUILD_DIR/attendancemgt/resources/" 2>/dev/null | wc -l) files${NC}"
    else
        echo -e "${RED}✗ Build failed!${NC}"
        exit 1
    fi
}

run() {
    echo -e "${YELLOW}Starting College ERP...${NC}"
    java -cp "$BUILD_DIR" "$MAIN_CLASS"
}

clean() {
    echo -e "${YELLOW}Cleaning build directory...${NC}"
    rm -rf "$BUILD_DIR"
    echo -e "${GREEN}✓ Cleaned${NC}"
}

case "${1:-build}" in
    build)
        build
        ;;
    run)
        if [ ! -d "$BUILD_DIR" ] || [ -z "$(ls -A "$BUILD_DIR/attendancemgt/"*.class 2>/dev/null)" ]; then
            echo -e "${YELLOW}No build found. Building first...${NC}"
            build
        fi
        run
        ;;
    clean)
        clean
        ;;
    *)
        echo "Usage: $0 [build|run|clean]"
        exit 1
        ;;
esac
