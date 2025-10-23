# OTK Assistant - Production Deployment Guide

Complete guide for deploying OTK Assistant in production using Docker Compose.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Quick Start](#quick-start)
3. [Development Setup](#development-setup)
4. [Production Setup](#production-setup)
5. [Configuration](#configuration)
6. [Maintenance](#maintenance)
7. [Monitoring](#monitoring)
8. [Troubleshooting](#troubleshooting)
9. [Security Hardening](#security-hardening)

---

## Prerequisites

### System Requirements

- **OS**: Linux (Ubuntu 20.04+ / Debian 11+ / RHEL 8+)
- **CPU**: 2+ cores recommended
- **RAM**: 4GB minimum, 8GB recommended
- **Disk**: 20GB minimum free space
- **Docker**: 20.10+
- **Docker Compose**: 2.0+

### Install Docker & Docker Compose

```bash
# Ubuntu/Debian
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh
sudo usermod -aG docker $USER

# Install Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# Verify installation
docker --version
docker-compose --version
```

---

## Quick Start

### 1. Development Mode (Local Testing)

```bash
# Clone the repository
git clone <repository-url>
cd otk-assistant

# Start services in development mode
docker-compose up -d

# View logs
docker-compose logs -f app

# Access application
# http://localhost:8080
# http://localhost:8080/actuator/health
```

### 2. Production Mode

```bash
# Build the application image
docker-compose -f docker-compose.prod.yml build

# Start all services
docker-compose -f docker-compose.prod.yml up -d

# Verify services are running
docker-compose -f docker-compose.prod.yml ps

# Check logs
docker-compose -f docker-compose.prod.yml logs -f
```

---

## Development Setup

### Standard Development

```bash
# Start development environment
docker-compose up -d

# Tail application logs
docker-compose logs -f app

# Restart after code changes
docker-compose restart app
```

### Hot Reload Development (Optional)

For development with hot reload (source code changes auto-reload):

```bash
# Start with dev profile
docker-compose --profile dev up -d app-dev

# Stop production app if running
docker-compose stop app
```

### Database Access

```bash
# Connect to PostgreSQL
docker-compose exec postgres psql -U otkreader -d otk

# Run SQL script
docker-compose exec -T postgres psql -U otkreader -d otk < your-script.sql

# Backup database
docker-compose exec postgres pg_dump -U otkreader otk > backup.sql
```

---

## Production Setup

### Step 1: Environment Configuration

1. **Copy environment template:**
   ```bash
   cp .env.prod.example .env.prod
   ```

2. **Edit `.env.prod` with your settings:**
   ```bash
   # Application
   APP_IMAGE=otk-assistant:v1.0.0
   SPRING_PROFILES_ACTIVE=production

   # Database
   POSTGRES_DB=otk
   POSTGRES_USER=otkreader
   POSTGRES_PASSWORD=<generate-strong-password>

   # Network
   ALLOWED_ORIGINS=https://yourdomain.com
   API_BASE_URL=https://api.yourdomain.com

   # SSL
   SSL_ENABLED=true
   ```

3. **Generate secure password:**
   ```bash
   # Generate random password
   openssl rand -base64 32
   
   # Update in .env.prod
   POSTGRES_PASSWORD=<generated-password>
   ```

### Step 2: SSL Certificates

#### Option A: Let's Encrypt (Recommended)

```bash
# Install Certbot
sudo apt-get update
sudo apt-get install -y certbot

# Stop nginx if running
docker-compose -f docker-compose.prod.yml stop nginx

# Obtain certificate
sudo certbot certonly --standalone -d yourdomain.com -d www.yourdomain.com

# Copy certificates
sudo cp /etc/letsencrypt/live/yourdomain.com/fullchain.pem nginx/ssl/cert.pem
sudo cp /etc/letsencrypt/live/yourdomain.com/privkey.pem nginx/ssl/key.pem
sudo chmod 644 nginx/ssl/cert.pem
sudo chmod 600 nginx/ssl/key.pem
```

#### Option B: Self-Signed (Development Only)

```bash
cd scripts
./generate-ssl-certs.sh
```

### Step 3: Build & Deploy

```bash
# Build application image
docker-compose -f docker-compose.prod.yml build app

# Tag image with version
docker tag otk-assistant:latest otk-assistant:v1.0.0

# Start all services
docker-compose -f docker-compose.prod.yml up -d

# Verify health
curl http://localhost:80/actuator/health
curl https://localhost:443/actuator/health -k
```

### Step 4: Verify Deployment

```bash
# Check all services are running
docker-compose -f docker-compose.prod.yml ps

# Should show:
# - otk-postgres-prod (healthy)
# - otk-assistant-prod (healthy)
# - otk-nginx-prod (healthy)
# - otk-backup-prod (running)

# Test endpoints
curl -X GET http://localhost/actuator/health
curl -X POST http://localhost/api/ost22 -H "Content-Type: application/json" -d '{}'
```

---

## Configuration

### Environment Variables

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `POSTGRES_DB` | Database name | `otk` | Yes |
| `POSTGRES_USER` | Database user | `otkreader` | Yes |
| `POSTGRES_PASSWORD` | Database password | `victoria` | Yes |
| `APP_IMAGE` | Docker image name | `otk-assistant:latest` | No |
| `SPRING_PROFILES_ACTIVE` | Spring profile | `production` | Yes |
| `ALLOWED_ORIGINS` | CORS allowed origins | - | Yes |
| `API_BASE_URL` | API base URL | - | Yes |
| `LOG_LEVEL_ROOT` | Root log level | `WARN` | No |
| `LOG_LEVEL_APP` | App log level | `INFO` | No |

### Resource Limits

Production compose includes resource limits:

**PostgreSQL:**
- CPU: 0.5-2.0 cores
- Memory: 512MB-2GB

**Application:**
- CPU: 0.5-2.0 cores
- Memory: 1GB-2GB

**Nginx:**
- CPU: 0.25-1.0 cores
- Memory: 128MB-256MB

Adjust in `docker-compose.prod.yml` under `deploy.resources`.

---

## Maintenance

### Backups

Automated backups run daily at 2 AM:

```bash
# Manual backup
docker-compose -f docker-compose.prod.yml exec postgres \
  pg_dump -U otkreader otk | gzip > backup_$(date +%Y%m%d).sql.gz

# List backups
docker-compose -f docker-compose.prod.yml exec backup ls -lh /backups

# Restore from backup
gunzip -c backup_20251023.sql.gz | \
  docker-compose -f docker-compose.prod.yml exec -T postgres \
  psql -U otkreader -d otk
```

### Updates

```bash
# Pull latest code
git pull origin main

# Rebuild application
docker-compose -f docker-compose.prod.yml build app

# Rolling update (zero downtime)
docker-compose -f docker-compose.prod.yml up -d --no-deps --build app

# Verify new version
docker-compose -f docker-compose.prod.yml logs app | head -20
```

### Scaling

```bash
# Scale application (requires load balancer)
docker-compose -f docker-compose.prod.yml up -d --scale app=3

# Verify replicas
docker-compose -f docker-compose.prod.yml ps app
```

### Log Management

```bash
# View logs
docker-compose -f docker-compose.prod.yml logs -f app

# View specific service
docker-compose -f docker-compose.prod.yml logs -f postgres

# Limit log output
docker-compose -f docker-compose.prod.yml logs --tail=100 app

# Export logs
docker-compose -f docker-compose.prod.yml logs --no-color > logs.txt

# Clear logs (caution!)
truncate -s 0 $(docker inspect --format='{{.LogPath}}' otk-assistant-prod)
```

---

## Monitoring

### Health Checks

```bash
# Application health
curl http://localhost/actuator/health

# Detailed health info
curl http://localhost/actuator/health | jq

# Database health
docker-compose -f docker-compose.prod.yml exec postgres \
  pg_isready -U otkreader -d otk

# All services health
docker-compose -f docker-compose.prod.yml ps
```

### Metrics

```bash
# Spring Boot metrics
curl http://localhost/actuator/metrics

# JVM memory
curl http://localhost/actuator/metrics/jvm.memory.used

# HTTP requests
curl http://localhost/actuator/metrics/http.server.requests
```

### Resource Usage

```bash
# Container stats
docker stats otk-assistant-prod otk-postgres-prod otk-nginx-prod

# Disk usage
docker system df

# Clean up unused resources
docker system prune -a --volumes
```

---

## Troubleshooting

### Application Won't Start

```bash
# Check logs
docker-compose -f docker-compose.prod.yml logs app

# Common issues:
# 1. Database not ready
docker-compose -f docker-compose.prod.yml ps postgres

# 2. Port already in use
sudo lsof -i :8080
sudo lsof -i :80

# 3. Environment variables missing
docker-compose -f docker-compose.prod.yml config
```

### Database Connection Issues

```bash
# Test database connection
docker-compose -f docker-compose.prod.yml exec postgres \
  psql -U otkreader -d otk -c "SELECT 1"

# Check database logs
docker-compose -f docker-compose.prod.yml logs postgres

# Verify network
docker network inspect otk-assistant_otk-backend
```

### Performance Issues

```bash
# Check resource usage
docker stats

# Database performance
docker-compose -f docker-compose.prod.yml exec postgres \
  psql -U otkreader -d otk -c "
  SELECT pid, query, state
  FROM pg_stat_activity
  WHERE state != 'idle';"

# Application thread dump
docker-compose -f docker-compose.prod.yml exec app \
  jstack 1 > threaddump.txt
```

### Reset Everything

```bash
# Stop all services
docker-compose -f docker-compose.prod.yml down

# Remove volumes (WARNING: deletes data!)
docker-compose -f docker-compose.prod.yml down -v

# Remove images
docker-compose -f docker-compose.prod.yml down --rmi all

# Clean rebuild
docker-compose -f docker-compose.prod.yml build --no-cache
docker-compose -f docker-compose.prod.yml up -d
```

---

## Security Hardening

### 1. Use Strong Passwords

```bash
# Generate secure password
openssl rand -base64 32 > secrets/db_password.txt

# Update in .env.prod
POSTGRES_PASSWORD=$(cat secrets/db_password.txt)
```

### 2. Restrict Network Access

```bash
# Firewall rules (UFW)
sudo ufw allow 22/tcp    # SSH
sudo ufw allow 80/tcp    # HTTP
sudo ufw allow 443/tcp   # HTTPS
sudo ufw enable

# Docker iptables (block direct container access)
sudo iptables -I DOCKER-USER -i eth0 ! -s 10.0.0.0/8 -j DROP
```

### 3. Enable Docker Content Trust

```bash
export DOCKER_CONTENT_TRUST=1
docker pull postgres:15-alpine
```

### 4. Regular Updates

```bash
# Update base images
docker-compose -f docker-compose.prod.yml pull

# Rebuild with latest patches
docker-compose -f docker-compose.prod.yml build --no-cache
```

### 5. Scan for Vulnerabilities

```bash
# Install Trivy
wget https://github.com/aquasecurity/trivy/releases/latest/download/trivy_Linux-64bit.tar.gz
tar zxvf trivy_Linux-64bit.tar.gz
sudo mv trivy /usr/local/bin/

# Scan images
trivy image otk-assistant:latest
trivy image postgres:15-alpine
```

---

## Additional Resources

- [Docker Documentation](https://docs.docker.com/)
- [Spring Boot Production Best Practices](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html)
- [PostgreSQL Tuning](https://www.postgresql.org/docs/current/runtime-config.html)
- [Nginx Optimization](https://www.nginx.com/blog/tuning-nginx/)

---

## Support

For issues and questions:
- Check logs: `docker-compose logs`
- Review documentation in `/docs`
- Open an issue on GitHub

**Last Updated:** 2025-10-23
