import { globalIgnores } from 'eslint/config'
import {
  defineConfigWithVueTs,
  vueTsConfigs,
  configureVueProject,
} from '@vue/eslint-config-typescript'
import pluginVue from 'eslint-plugin-vue'
import pluginVitest from '@vitest/eslint-plugin'
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import pluginCypress from 'eslint-plugin-cypress'
import pluginOxlint from 'eslint-plugin-oxlint'
import skipFormatting from '@vue/eslint-config-prettier/skip-formatting'

// 关键修改1：允许 Vue 文件的 <script> 块使用 js/ts/tsx（默认只允许 ts/tsx）
configureVueProject({
  scriptLangs: ['ts', 'js', 'tsx'], // 新增 'js'，允许 JavaScript 语言
})

export default defineConfigWithVueTs(
  {
    name: 'app/files-to-lint',
    files: ['**/*.{ts,mts,tsx,vue,js,mjs}'], // 可选：新增 js/mjs 后缀，确保 JS 文件被正确识别
  },

  globalIgnores(['**/dist/**', '**/dist-ssr/**', '**/coverage/**']),

  pluginVue.configs['flat/essential'],
  vueTsConfigs.recommended,

  {
    ...pluginVitest.configs.recommended,
    files: ['src/**/__tests__/*'],
  },

  {
    ...pluginCypress.configs.recommended,
    files: ['cypress/e2e/**/*.{cy,spec}.{js,ts,jsx,tsx}', 'cypress/support/**/*.{js,ts,jsx,tsx}'],
  },

  // 关键修改2：禁用/调整 vue/block-lang 规则（解决爆红核心）
  {
    rules: {
      // 完全禁用
      'vue/block-lang': 'off',
    },
  },

  ...pluginOxlint.configs['flat/recommended'],
  skipFormatting,
)
