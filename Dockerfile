FROM eclipse-temurin:11-jdk-jammy

# Install dependencies
RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    zip \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# Install SDKMAN
RUN curl -s "https://get.sdkman.io" | bash

# Install Groovy via SDKMAN
RUN bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install groovy"

# Ensure Groovy is on PATH
ENV PATH="/root/.sdkman/candidates/groovy/current/bin:${PATH}"

WORKDIR /workspace

CMD ["groovy", "--version"]
