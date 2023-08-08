import { defineConfig } from 'vite'
import { sveltekit } from '@sveltejs/kit/vite'

export default defineConfig({
    server: {
        proxy: {
            '/api/': {
                target: 'http://localhost:8080/',
                secure: false,
                changeOrigin: true,
            }
        }
    },
    plugins: [sveltekit()],
})
