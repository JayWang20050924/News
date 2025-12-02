/** @type {import('tailwindcss').Config} */
export default {
  content: [
    './index.html',
    './src/**/*.{vue,js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {
      colors: {
        dark: '#121212',
        'gray-900': 'rgb(30 30 30)',
        'gray-800': 'rgb(45 45 45)',
        'gray-700': '#3D3D3D',
        'gray-300': '#B3B3B3',
        'gray-100': '#F0F0F0',
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif'],
      },
      utilities: {
        'content-auto': {
          'content-visibility': 'auto',
        },
        'input-focus': {
          '&:focus': {
            // 改为通过回调函数获取 theme
            'border-color': ({ theme }) => theme('colors.gray.300'),
            'ring-width': '2px',
            'ring-color': 'rgba(209, 213, 219, 0.3)',
            outline: 'none',
          },
        },
        'btn-hover': {
          'transition-property': 'all',
          'transition-duration': '300ms',
          '&:hover': {
            // 改为通过回调函数获取 theme
            'background-color': ({ theme }) => theme('colors.gray.100'),
            'box-shadow': '0 10px 15px -3px rgba(55, 65, 81, 0.2)',
          },
        },
        'card-hover': {
          'transition-property': 'all',
          'transition-duration': '300ms',
          '&:hover': {
            'box-shadow': '0 20px 25px -5px rgba(17, 24, 39, 0.3), 0 10px 10px -5px rgba(17, 24, 39, 0.1)',
            transform: 'translateY(-0.25rem)',
          },
        },
        'mobile-nav-active': {
          // 改为通过回调函数获取 theme
          'background-color': ({ theme }) => theme('colors.gray.800'),
          color: 'white',
        },
      },
    },
  },
  plugins: [],
}
