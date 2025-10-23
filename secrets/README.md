# Secrets Directory

This directory contains sensitive information used by the Docker containers.

## Files

### `db_password.txt`
Contains the PostgreSQL database password.

**Format:** Plain text, single line
**Example:** `your_secure_password_here`

## Security Best Practices

### For Development
- Use simple passwords for local development
- Current default: `victoria` (as per existing setup)
- This password is acceptable for local development only

### For Production

1. **Generate Strong Passwords**
   ```bash
   # Generate a secure random password
   openssl rand -base64 32 > secrets/db_password.txt
   ```

2. **Set Proper File Permissions**
   ```bash
   chmod 600 secrets/db_password.txt
   chown root:root secrets/db_password.txt
   ```

3. **Never Commit Secrets**
   - Ensure `.gitignore` includes the `secrets/` directory
   - Use environment-specific secret files
   - Consider using proper secret management tools like:
     - HashiCorp Vault
     - AWS Secrets Manager
     - Azure Key Vault
     - Docker Swarm secrets
     - Kubernetes secrets

4. **Rotate Passwords Regularly**
   - Change passwords every 90 days minimum
   - Update in both the secret file and database
   - Document rotation procedures

## File Structure

```
secrets/
├── README.md              # This file
├── db_password.txt        # Database password (DO NOT COMMIT)
├── jwt_secret.txt         # JWT signing secret (if needed)
└── api_keys.txt           # Third-party API keys (if needed)
```

## Usage in Docker Compose

Production compose file uses Docker secrets:
```yaml
secrets:
  db_password:
    file: ./secrets/db_password.txt
```

Accessed in containers via:
```
/run/secrets/db_password
```

## Troubleshooting

If you encounter permission errors:
```bash
# Ensure files are readable by Docker
chmod 644 secrets/db_password.txt

# Or for maximum security (only readable by owner)
chmod 600 secrets/db_password.txt
```

## Emergency Recovery

If secrets are lost:
1. Stop all services
2. Update the secret files with new values
3. Update database passwords if needed
4. Restart services
5. Verify all services can authenticate

## Additional Resources

- [Docker Secrets Documentation](https://docs.docker.com/engine/swarm/secrets/)
- [OWASP Password Storage Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html)
