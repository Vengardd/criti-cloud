# =========================
# Stage 1: Build native image
# =========================
# Use GraalVM image with native-image already installed
FROM ghcr.io/graalvm/native-image-community:21 AS builder

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Install findutils if needed by Gradle scripts
RUN microdnf install findutils

# Build native image using Gradle
RUN ./gradlew clean nativeCompile

# =========================
# Stage 2: Minimal runtime image
# =========================
FROM debian:bookworm-slim

WORKDIR /app

# Install required libraries for native-image binary
RUN apt-get update && apt-get install -y \
    libz1 \
    libstdc++6 \
    && rm -rf /var/lib/apt/lists/*

# Copy native binary
COPY --from=builder /app/build/native/nativeCompile/app /app/app

# Expose HTTP port
EXPOSE 8080

# Run native executable
ENTRYPOINT ["/app/app"]