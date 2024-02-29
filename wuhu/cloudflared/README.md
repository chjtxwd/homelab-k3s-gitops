create secret with
```
kubectl create secret generic cloudflared-secret \
    --from-literal=token=YOURTOKEN
```