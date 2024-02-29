create secret with
```
apiVersion: v1
kind: Secret
metadata:
  name: cloudflared-secret
type: Opaque
data:
  token: BASE64_ENCODED_TOKEN
```
BASE64_ENCODED_TOKEN use echo -n "YOUR_TOKEN" | base64