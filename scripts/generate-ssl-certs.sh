#!/bin/bash
# Generate self-signed SSL certificates for development/testing
# For production, use proper certificates from Let's Encrypt or a CA

set -e

SSL_DIR="../nginx/ssl"
CERT_FILE="$SSL_DIR/cert.pem"
KEY_FILE="$SSL_DIR/key.pem"
DAYS_VALID=365

echo "Generating self-signed SSL certificate for development..."

# Create SSL directory if it doesn't exist
mkdir -p "$SSL_DIR"

# Generate self-signed certificate
openssl req -x509 -nodes -days $DAYS_VALID -newkey rsa:2048 \
    -keyout "$KEY_FILE" \
    -out "$CERT_FILE" \
    -subj "/C=US/ST=State/L=City/O=Development/OU=IT/CN=localhost" \
    -addext "subjectAltName=DNS:localhost,DNS:*.localhost,IP:127.0.0.1"

# Set appropriate permissions
chmod 644 "$CERT_FILE"
chmod 600 "$KEY_FILE"

echo "✓ SSL certificate generated successfully!"
echo "  Certificate: $CERT_FILE"
echo "  Private Key: $KEY_FILE"
echo "  Valid for: $DAYS_VALID days"
echo ""
echo "NOTE: This is a self-signed certificate for DEVELOPMENT ONLY."
echo "For production, use certificates from Let's Encrypt or a trusted CA."
