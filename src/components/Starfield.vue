<template>
  <!-- 星空画布，作为背景层 -->
  <canvas
    ref="starfieldCanvas"
    class="starfield"
    aria-hidden="true"
  ></canvas>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue';

// 常量配置（可根据需求调整）
const STAR_COUNT = 250; // 星星数量
const STAR_COLOR_BASE = 'rgba(240, 240, 240, '; // 星星基础颜色（配合亮度）
const BG_OPACITY = 0.2; // 背景拖影透明度
const BG_COLOR = `rgba(18, 18, 18, ${BG_OPACITY})`; // 背景拖影颜色

// DOM元素引用
const starfieldCanvas = ref(null); // canvas元素
let ctx = null; // 2D绘图上下文

// 状态管理
const stars = ref([]); // 星星数据数组（响应式）
let animationId = null; // 动画帧ID（用于清理）
let maskCheckInterval = null; // 加载遮罩检查定时器


/**
 * 调整Canvas尺寸以适配窗口
 */
const resizeCanvas = () => {
  if (!starfieldCanvas.value) return;
  starfieldCanvas.value.width = window.innerWidth;
  starfieldCanvas.value.height = window.innerHeight;
};


/**
 * 初始化星星数据
 */
const initStars = () => {
  const canvas = starfieldCanvas.value;
  if (!canvas) return;

  stars.value = Array.from({ length: STAR_COUNT }, () => ({
    x: Math.random() * canvas.width, // 随机X坐标
    y: Math.random() * canvas.height, // 随机Y坐标
    radius: Math.random() * 1.2 + 0.3, // 星星大小（0.3-1.5）
    brightness: Math.random() * 0.7 + 0.3, // 亮度（0.3-1.0）
    speedX: (Math.random() - 0.5) * 0.08, // X方向速度
    speedY: (Math.random() - 0.5) * 0.08, // Y方向速度
    flickerRate: Math.random() * 0.015 + 0.003 // 闪烁频率
  }));
};


/**
 * 更新星星状态（移动、闪烁）
 */
const updateStars = () => {
  const canvas = starfieldCanvas.value;
  if (!canvas) return;

  stars.value.forEach(star => {
    // 星星移动
    star.x += star.speedX;
    star.y += star.speedY;

    // 边界循环（超出屏幕后从另一侧出现）
    if (star.x < 0) star.x = canvas.width;
    if (star.x > canvas.width) star.x = 0;
    if (star.y < 0) star.y = canvas.height;
    if (star.y > canvas.height) star.y = 0;

    // 平滑闪烁效果（限制亮度范围）
    star.brightness += (Math.random() - 0.5) * star.flickerRate;
    star.brightness = Math.max(0.3, Math.min(1, star.brightness));
  });
};


/**
 * 绘制星星到画布
 */
const drawStars = () => {
  if (!ctx) return;
  const canvas = starfieldCanvas.value;
  if (!canvas) return;

  // 绘制半透明背景（产生拖影效果，增强深邃感）
  ctx.fillStyle = BG_COLOR;
  ctx.fillRect(0, 0, canvas.width, canvas.height);

  // 绘制每个星星
  stars.value.forEach(star => {
    ctx.beginPath();
    ctx.arc(star.x, star.y, star.radius, 0, Math.PI * 2);
    ctx.fillStyle = `${STAR_COLOR_BASE}${star.brightness})`;
    ctx.fill();
  });
};


/**
 * 动画循环
 */
const animate = () => {
  updateStars();
  drawStars();
  animationId = requestAnimationFrame(animate);
};


/**
 * 初始化星空效果
 */
const initStarfield = () => {
  const canvas = starfieldCanvas.value;
  if (!canvas) return;

  // 获取绘图上下文
  ctx = canvas.getContext('2d');
  // 初始化尺寸和星星数据
  resizeCanvas();
  initStars();
  // 启动动画
  animate();
};


// 组件生命周期管理
onMounted(() => {
  // 检查加载遮罩（如果存在）
  const loadMask = document.getElementById('load-mask');

  if (loadMask) {
    // 定时检查遮罩是否隐藏
    maskCheckInterval = setInterval(() => {
      const isMaskHidden =
        loadMask.style.display === 'none' ||
        getComputedStyle(loadMask).display === 'none';

      if (isMaskHidden) {
        clearInterval(maskCheckInterval); // 清除检查定时器
        initStarfield(); // 初始化星空
      }
    }, 100); // 每100ms检查一次
  } else {
    // 无遮罩时直接初始化
    initStarfield();
  }

  // 监听窗口 resize 事件（动态调整画布尺寸）
  window.addEventListener('resize', resizeCanvas);
});


onUnmounted(() => {
  // 清理动画帧（避免组件卸载后仍运行）
  if (animationId) cancelAnimationFrame(animationId);
  // 清理遮罩检查定时器
  if (maskCheckInterval) clearInterval(maskCheckInterval);
  // 移除窗口 resize 监听
  window.removeEventListener('resize', resizeCanvas);
});
</script>

<style scoped>
.starfield {
  /* 固定定位作为背景层 */
  position: fixed;
  top: 0;
  left: 0;
  z-index: -1; /* 置于内容下方 */
  /* 避免干扰鼠标交互 */
  pointer-events: none;
}
</style>
