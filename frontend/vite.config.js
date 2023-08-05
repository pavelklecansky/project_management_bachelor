import { defineConfig } from 'vite'
import { svelte } from '@sveltejs/vite-plugin-svelte'

export default defineConfig({
    server: {
        proxy: {
            '/api/': {
                target: process.env.PROXY_API || 'http://localhost:8080/',
                secure: false,
                changeOrigin: true,
            }
        }
    },
    plugins: [svelte()],
})
