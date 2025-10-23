# Docker Setup for OTK Assistant

Quick reference guide for running OTK Assistant with Docker.

> **📘 For detailed production deployment instructions, see [DEPLOYMENT.md](DEPLOYMENT.md)**

## Prerequisites

- Docker Engine 20.10+
- Docker Compose 2.0+
- At least 2GB of available RAM (4GB recommended for production)

## Quick Start

### 1. Production Mode

Run the application with PostgreSQL database:

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop services
docker-compose down
```

The application will be available at: http://localhost:8080

### 2. Development Mode

For development with hot reload:

```bash
# Start in development mode
docker-compose --profile dev up -d

# View logs
docker-compose logs -f app-dev

# Stop services
docker-compose down
```

## Environment Configuration

### Using .env file

1. Copy the example environment file:
```bash
cp .env.example .env
```

2. Edit `.env` with your configuration:
```bash
# Database Configuration
DATASOURCE_URL=jdbc:postgresql://localhost:5432/otk?ssl=false
DATASOURCE_USERNAME=otkreader
DATASOURCE_PASSWORD=your_password_here

# Application Configuration
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=docker

# CORS & API Configuration
ALLOWED_ORIGINS=http://localhost:3000,http://localhost:8080
API_BASE_URL=http://localhost:8080

# Logging Configuration
LOG_LEVEL_ROOT=INFO
LOG_LEVEL_APP=DEBUG
```

### Using docker-compose.override.yml

Create a `docker-compose.override.yml` file for local overrides:

```yaml
version: '3.8'
services:
  app:
    environment:
      DATASOURCE_URL: jdbc:postgresql://postgres:5432/otk?ssl=false
      DATASOURCE_USERNAME: your_username
      DATASOURCE_PASSWORD: your_password
```

## Building the Docker Image

### Build production image

```bash
# Build the image
docker build -t otk-assistant:latest .

# Run the container
docker run -d \
  --name otk-assistant \
  -p 8080:8080 \
  -e DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/otk \
  -e DATASOURCE_USERNAME=your_username \
  -e DATASOURCE_PASSWORD=your_password \
  otk-assistant:latest
```

### Build development image

```bash
# Build the development image
docker build -f Dockerfile.dev -t otk-assistant:dev .

# Run with volume mounting for hot reload
docker run -d \
  --name otk-assistant-dev \
  -p 8080:8080 \
  -v $(pwd):/app \
  -e DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/otk \
  otk-assistant:dev
```

## Health Checks

The application includes health checks:

- **Application**: http://localhost:8080/actuator/health
- **Database**: Built-in PostgreSQL health check

Check container health:
```bash
docker-compose ps
```

## Troubleshooting

### Common Issues

1. **Port already in use**
   ```bash
   # Check what's using the port
   lsof -i :8080
   
   # Kill the process or change the port in docker-compose.yml
   ```

2. **Database connection issues**
   ```bash
   # Check database logs
   docker-compose logs postgres
   
   # Check if database is ready
   docker-compose exec postgres pg_isready -U otkreader -d otk
   ```

3. **Application startup issues**
   ```bash
   # Check application logs
   docker-compose logs app
   
   # Check if all environment variables are set
   docker-compose exec app env | grep -E "(DATASOURCE|SERVER|SPRING)"
   ```

### Logs

View logs for specific services:
```bash
# All services
docker-compose logs

# Specific service
docker-compose logs app
docker-compose logs postgres

# Follow logs in real-time
docker-compose logs -f app
```

### Cleanup

Remove all containers, networks, and volumes:
```bash
# Stop and remove containers
docker-compose down

# Remove volumes (WARNING: This will delete database data)
docker-compose down -v

# Remove images
docker-compose down --rmi all
```

## Production Deployment

For production deployment:

1. **Use environment-specific configuration**
2. **Set up proper secrets management**
3. **Configure reverse proxy (nginx/traefik)**
4. **Set up monitoring and logging**
5. **Use container orchestration (Kubernetes/Docker Swarm)**

### Example production docker-compose.yml

```yaml
version: '3.8'
services:
  app:
    image: otk-assistant:latest
    environment:
      DATASOURCE_URL: ${DATASOURCE_URL}
      DATASOURCE_USERNAME: ${DATASOURCE_USERNAME}
      DATASOURCE_PASSWORD: ${DATASOURCE_PASSWORD}
      SPRING_PROFILES_ACTIVE: production
    ports:
      - "8080:8080"
    restart: unless-stopped
    deploy:
      resources:
        limits:
          memory: 1G
        reservations:
          memory: 512M
```

## Security Considerations

1. **Never commit `.env` files with real credentials**
2. **Use Docker secrets for sensitive data in production**
3. **Run containers as non-root user (already configured)**
4. **Keep base images updated**
5. **Use specific image tags instead of `latest`**

## Performance Tuning

The Dockerfile includes JVM optimizations for containers:
- `-XX:+UseContainerSupport`: Enable container-aware JVM
- `-XX:MaxRAMPercentage=75.0`: Use 75% of available memory
- Memory limits: 512MB max, 256MB initial

Adjust these settings based on your requirements.