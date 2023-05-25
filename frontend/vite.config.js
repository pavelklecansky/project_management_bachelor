const { appConfig } = require('./package.json')
const viteMainJs = require('vite-main-js')
const tailwind = require('tailwindcss')
const postcssImport = require('postcss-import')
const autoPreprocess = require('svelte-preprocess')
const { svelte } = require('@sveltejs/vite-plugin-svelte')
const { port } = appConfig
const production = process.env.NODE_ENV === 'production'
module.exports = {
  server: {
    port: port,
    proxy: {
      '/api/': {
        target: process.env.PROXY_API || 'http://localhost:8080/',
        secure: false,
        changeOrigin: true,
      }
    }
  },
  build: {
    cssCodeSplit: false,
  },
  optimizeDeps: {
    exclude: ['@roxi/routify'],
  },
  resolve: {
    dedupe: ['@roxi/routify'],
  },
  plugins: [
    viteMainJs(),
    svelte({
      preprocess: [
        autoPreprocess({
          postcss: {
            plugins: [
              tailwind({
                mode: 'jit',
                darkMode: 'class',
                future: {
                  removeDeprecatedGapUtilities: true,
                  purgeLayersByDefault: true,
                },
                plugins: [],
                purge: {
                  content: ['./src/**/*.svelte'],
                },
              }),
              postcssImport,
            ],
          },
        }),
      ],
      emitCss: true,
      hot: !production,
    }),
  ],
}
