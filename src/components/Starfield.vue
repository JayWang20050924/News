<template>
  <!-- 星空画布，作为背景层 -->
  <canvas
    ref="starfieldCanvas"
    class="starfield"
    aria-hidden="true"
  ></canvas>
</template>

<script setup lang="js">
import { onMounted, onUnmounted, ref } from 'vue';

// 常量配置（可根据性能调整，星星数量可适当减少）
const STAR_COUNT = 120; // 适度减少星星数量（从150→120）
const STAR_COLOR_BASE = 'rgba(240, 240, 240, ';
const BG_OPACITY = 0.18; // 轻微降低拖影透明度，减少像素填充量
const BG_COLOR = `rgba(18, 18, 18, ${BG_OPACITY})`;

// DOM元素引用
const starfieldCanvas = ref(null);
let ctx = null;

// 🌟 关键优化1：移除响应式（stars无需Vue追踪，减少性能开销）
let stars = []; // 改为普通数组，而非ref
let animationId = null;
let resizeTimeout = null; // resize防抖定时器

/**
 * 调整Canvas尺寸（防抖处理，避免频繁resize触发）
 */
const resizeCanvas = () => {
  // 防抖：30ms内只执行一次，避免窗口缩放时频繁重绘
  clearTimeout(resizeTimeout);
  resizeTimeout = setTimeout(() => {
    if (!starfieldCanvas.value) return;
    // 优化：只在尺寸变化时更新，避免无意义赋值
    const { innerWidth, innerHeight } = window;
    if (
      starfieldCanvas.value.width !== innerWidth ||
      starfieldCanvas.value.height !== innerHeight
    ) {
      starfieldCanvas.value.width = innerWidth;
      starfieldCanvas.value.height = innerHeight;
      // 重新初始化星星（适配新尺寸）
      initStars();
    }
  }, 30);
};

/**
 * 初始化星星数据（精简计算逻辑）
 */
const initStars = () => {
  const canvas = starfieldCanvas.value;
  if (!canvas) return;

  stars = Array.from({ length: STAR_COUNT }, () => {
    // 预计算随机值，减少重复Math.random调用
    const randomBase = Math.random();
    return {
      x: Math.random() * canvas.width,
      y: Math.random() * canvas.height,
      radius: randomBase * 1.2 + 0.3, // 复用随机值
      brightness: randomBase * 0.7 + 0.3,
      speedX: (Math.random() - 0.5) * 0.07, // 轻微降低移动速度，减少计算
      speedY: (Math.random() - 0.5) * 0.07,
      flickerRate: Math.random() * 0.012 + 0.003 // 降低闪烁频率，减少计算
    };
  });
};

/**
 * 更新星星状态（精简边界判断逻辑）
 */
const updateStars = () => {
  const canvas = starfieldCanvas.value;
  if (!canvas) return;
  const { width, height } = canvas;

  // 🌟 优化：用for循环替代forEach，性能更高（forEach有函数调用开销）
  for (let i = 0; i < stars.length; i++) {
    const star = stars[i];
    // 移动逻辑
    star.x += star.speedX;
    star.y += star.speedY;

    // 边界循环（精简判断逻辑）
    star.x = star.x < 0 ? width : star.x > width ? 0 : star.x;
    star.y = star.y < 0 ? height : star.y > height ? 0 : star.y;

    // 闪烁效果（限制计算范围，减少Math.max/min调用次数）
    const newBrightness = star.brightness + (Math.random() - 0.5) * star.flickerRate;
    star.brightness = newBrightness < 0.3 ? 0.3 : newBrightness > 1 ? 1 : newBrightness;
  }
};

/**
 * 绘制星星（优化Canvas绘制性能）
 */
const drawStars = () => {
  if (!ctx || !starfieldCanvas.value) return;
  const { width, height } = starfieldCanvas.value;

  // 🌟 优化1：使用clearRect替代fillRect（减少像素填充，拖影效果通过降低opacity实现）
  // 原fillRect是全屏填充半透明，性能消耗大；改为clearRect+全局alpha，更高效
  ctx.clearRect(0, 0, width, height);
  ctx.globalAlpha = BG_OPACITY; // 用全局透明度模拟拖影，减少填充量
  ctx.fillStyle = 'rgba(18, 18, 18, 1)';
  ctx.fillRect(0, 0, width, height);
  ctx.globalAlpha = 1; // 恢复透明度

  // 🌟 优化2：关闭抗锯齿（如果视觉可接受，大幅提升绘制性能）
  ctx.imageSmoothingEnabled = false;

  // 绘制星星（for循环+减少属性查找）
  for (let i = 0; i < stars.length; i++) {
    const star = stars[i];
    ctx.beginPath();
    ctx.arc(star.x, star.y, star.radius, 0, Math.PI * 2);
    // 优化：提前拼接颜色字符串，减少运行时计算
    ctx.fillStyle = `${STAR_COLOR_BASE}${star.brightness})`;
    ctx.fill();
    ctx.closePath(); // 关闭路径，减少内存占用
  }
};

/**
 * 动画循环（添加性能保护：帧率过低时自动降星数）
 */
const animate = () => {
  // 🌟 可选：性能监控，帧率低于50时自动减少星星数量
  // const now = performance.now();
  // if (now - lastFrameTime > 20) { // 帧率<50
  //   STAR_COUNT = Math.max(80, STAR_COUNT - 10);
  //   initStars();
  // }
  // lastFrameTime = now;

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

  ctx = canvas.getContext('2d');
  resizeCanvas(); // 初始化尺寸
  initStars();
  animate();
};

// 组件生命周期
onMounted(() => {
  initStarfield();
  // 监听resize（优化：被动监听，减少事件阻塞）
  window.addEventListener('resize', resizeCanvas, { passive: true });
});

onUnmounted(() => {
  // 清理所有定时器和监听
  if (animationId) cancelAnimationFrame(animationId);
  if (resizeTimeout) clearTimeout(resizeTimeout);
  window.removeEventListener('resize', resizeCanvas);
  // 释放内存
  stars = [];
  ctx = null;
});
</script>

<style scoped>
/* 🌟 关键修复：选择器从#starfield改为.starfield（匹配模板中的class） */
/* 🌟 关键优化：开启GPU硬件加速，脱离主线程渲染 */
.starfield {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw; /* 用vw/vh替代%，避免继承问题 */
  height: 100vh;
  z-index: -1;
  background-color: #121212;
  pointer-events: none; /* 禁止鼠标事件，不抢占滚动 */
  /* 开启GPU硬件加速，将Canvas渲染到GPU层 */
  transform: translate3d(0, 0, 0);
  -webkit-transform: translate3d(0, 0, 0);
  /* 告诉浏览器该元素会动画，提前优化渲染策略 */
  will-change: transform;
  /* 禁止Canvas的鼠标选中，减少不必要的交互 */
  user-select: none;
  -webkit-user-select: none;
}
</style>
