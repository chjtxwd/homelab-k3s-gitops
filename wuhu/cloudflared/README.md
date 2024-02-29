create secret with
```
apiVersion: v1
kind: Secret
metadata:
  name: cloudflared-secret
type: Opaque
data:
  token: token
```