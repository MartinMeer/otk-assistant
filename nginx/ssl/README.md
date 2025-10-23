# SSL Certificates

This directory contains SSL certificates for HTTPS support.

## Development / Testing

For local development, self-signed certificates have been generated:
- `cert.pem` - SSL certificate
- `key.pem` - Private key

### Regenerate Certificates
```bash
cd scripts
./generate-ssl-certs.sh
```

## Production

For production, you should use proper certificates from a Certificate Authority.

### Option 1: Let's Encrypt (Recommended - FREE)

1. **Using Certbot with Docker:**
   ```bash
   # Install certbot
   docker run -it --rm \
     -v /etc/letsencrypt:/etc/letsencrypt \
     -v /var/lib/letsencrypt:/var/lib/letsencrypt \
     -p 80:80 \
     certbot/certbot certonly --standalone \
     -d yourdomain.com -d www.yourdomain.com
   
   # Copy certificates
   cp /etc/letsencrypt/live/yourdomain.com/fullchain.pem nginx/ssl/cert.pem
   cp /etc/letsencrypt/live/yourdomain.com/privkey.pem nginx/ssl/key.pem
   ```

2. **Auto-renewal setup:**
   Add to crontab:
   ```
   0 0 * * * docker run --rm -v /etc/letsencrypt:/etc/letsencrypt certbot/certbot renew --quiet
   ```

### Option 2: Commercial CA

1. Generate a Certificate Signing Request (CSR):
   ```bash
   openssl req -new -newkey rsa:2048 -nodes \
     -keyout nginx/ssl/key.pem \
     -out nginx/ssl/cert.csr \
     -subj "/C=US/ST=State/L=City/O=Company/CN=yourdomain.com"
   ```

2. Submit the CSR to your CA
3. Save the certificate as `nginx/ssl/cert.pem`

### Option 3: Self-Signed (NOT for production)

Only use self-signed certificates for internal testing:
```bash
cd scripts
./generate-ssl-certs.sh
```

## Certificate Files

- `cert.pem` - SSL certificate (public)
- `key.pem` - Private key (keep secure!)
- `dhparam.pem` - Diffie-Hellman parameters (optional, for enhanced security)

## Security Best Practices

1. **Protect Private Keys**
   ```bash
   chmod 600 nginx/ssl/key.pem
   chown root:root nginx/ssl/key.pem
   ```

2. **Never Commit Private Keys**
   - Ensure `.gitignore` includes `*.pem` files
   - Store production keys in secure vault

3. **Use Strong Ciphers**
   - Nginx configuration already includes modern cipher suites
   - Disable TLS 1.0 and 1.1 (already done)

4. **Enable OCSP Stapling**
   - Already configured in nginx
   - Improves SSL/TLS handshake performance

5. **Certificate Expiration**
   - Monitor certificate expiration dates
   - Set up alerts 30 days before expiry
   - Let's Encrypt certificates expire after 90 days

## Testing SSL Configuration

Test your SSL configuration:
```bash
# Test certificate validity
openssl x509 -in nginx/ssl/cert.pem -text -noout

# Test SSL/TLS connection
openssl s_client -connect localhost:443 -servername localhost

# Check certificate expiration
openssl x509 -in nginx/ssl/cert.pem -noout -dates

# Online SSL testing (production only)
# Visit: https://www.ssllabs.com/ssltest/
```

## Troubleshooting

### Certificate mismatch errors
- Ensure certificate CN or SAN matches your domain
- Check certificate and key are a matching pair:
  ```bash
  openssl x509 -noout -modulus -in cert.pem | openssl md5
  openssl rsa -noout -modulus -in key.pem | openssl md5
  ```

### Permission errors
```bash
chmod 644 nginx/ssl/cert.pem
chmod 600 nginx/ssl/key.pem
```

## Additional Resources

- [Let's Encrypt Documentation](https://letsencrypt.org/docs/)
- [Mozilla SSL Configuration Generator](https://ssl-config.mozilla.org/)
- [SSL Labs Testing Tool](https://www.ssllabs.com/ssltest/)
