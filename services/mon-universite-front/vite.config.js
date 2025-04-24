import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    host: '0.0.0.0', // Allow external connections (required for Docker)
    port: 5173,       // Match the port exposed in docker-compose.yml
    proxy: {
      '/api': {
        target: 'http://gateway-service:8222', // Proxy API requests to the gateway-service
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''), // Remove '/api' prefix when forwarding
      },
    },
  },
});