// src/stores/position.js
import { defineStore } from 'pinia'

// 定义并导出positionStore
export const usePositionStore = defineStore('position', {
  // 状态：存储导航指示器的位置信息
  state: () => ({
    // 导航指示器的位置（left: 左偏移量, width: 宽度）
    navIndicatorPos: {
      left: 0,
      width: 0,
    },
  }),

  // 行动：提供修改状态的方法
  actions: {
    /**
     * 设置导航指示器的位置
     * @param {Object} pos - 位置对象，包含left和width属性
     */
    setNavIndicatorPos(pos) {
      // 验证参数，确保传入的是合法的数值
      if (typeof pos.left === 'number' && typeof pos.width === 'number') {
        this.navIndicatorPos = {
          left: pos.left,
          width: pos.width,
        }
      } else {
        console.warn('导航指示器位置参数不合法，需传入包含left和width的数字对象')
      }
    },

    /**
     * 重置导航指示器位置
     */
    resetNavIndicatorPos() {
      this.navIndicatorPos = {
        left: 0,
        width: 0,
      }
    },
  },

  // 计算属性（基于位置派生新值）
  getters: {
    // 获取指示器的右偏移量
    navIndicatorRight: (state) => {
      return state.navIndicatorPos.left + state.navIndicatorPos.width
    },
  },
})
