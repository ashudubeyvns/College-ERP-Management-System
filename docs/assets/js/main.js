/**
 * Attendance Management System - GitHub Pages Site
 * Main JavaScript functionality
 */

// ===== Mobile Nav Toggle =====
document.addEventListener('DOMContentLoaded', () => {
  const navToggle = document.getElementById('navToggle');
  const navMenu = document.getElementById('navMenu');

  if (navToggle && navMenu) {
    navToggle.addEventListener('click', () => {
      navToggle.classList.toggle('active');
      navMenu.classList.toggle('active');
    });

    // Close menu on link click
    document.querySelectorAll('.nav-link').forEach(link => {
      link.addEventListener('click', () => {
        navToggle.classList.remove('active');
        navMenu.classList.remove('active');
      });
    });
  }
});

// ===== Smooth Scroll (fallback for older browsers) =====
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
  anchor.addEventListener('click', function(e) {
    const href = this.getAttribute('href');
    if (href === '#') return;
    const target = document.querySelector(href);
    if (target) {
      e.preventDefault();
      target.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    }
  });
});

// ===== Copy Code Button =====
function copyCode(button) {
  const codeBlock = button.closest('.code-block');
  const code = codeBlock.querySelector('code');

  if (!code) return;

  // Get text content (removes nested HTML)
  const text = code.textContent || code.innerText;

  // Use the Clipboard API
  if (navigator.clipboard && navigator.clipboard.writeText) {
    navigator.clipboard.writeText(text.trim()).then(() => {
      showCopiedFeedback(button);
    }).catch(() => {
      fallbackCopy(text, button);
    });
  } else {
    fallbackCopy(text, button);
  }
}

// Fallback copy method
function fallbackCopy(text, button) {
  const textarea = document.createElement('textarea');
  textarea.value = text.trim();
  textarea.style.position = 'fixed';
  textarea.style.opacity = '0';
  document.body.appendChild(textarea);
  textarea.select();

  try {
    document.execCommand('copy');
    showCopiedFeedback(button);
  } catch (err) {
    console.error('Copy failed', err);
  }

  document.body.removeChild(textarea);
}

// Show "Copied!" feedback
function showCopiedFeedback(button) {
  const originalIcon = button.innerHTML;
  button.innerHTML = '<i class="fas fa-check"></i>';
  button.style.color = '#27c93f';

  setTimeout(() => {
    button.innerHTML = originalIcon;
    button.style.color = '';
  }, 2000);
}

// ===== Navbar Scroll Effect =====
let lastScroll = 0;
const navbar = document.getElementById('navbar');

window.addEventListener('scroll', () => {
  const currentScroll = window.pageYOffset;

  if (currentScroll > 100) {
    navbar.style.background = 'rgba(44, 62, 80, 0.98)';
  } else {
    navbar.style.background = 'rgba(44, 62, 80, 0.95)';
  }

  lastScroll = currentScroll;
});

// ===== Intersection Observer for Animations =====
const animateElements = document.querySelectorAll('.feature-card, .screenshot-card, .start-step, .tech-item');

const observer = new IntersectionObserver((entries) => {
  entries.forEach((entry, index) => {
    if (entry.isIntersecting) {
      entry.target.style.opacity = '1';
      entry.target.style.transform = 'translateY(0)';
      observer.unobserve(entry.target);
    }
  });
}, {
  threshold: 0.1,
  rootMargin: '0px 0px -50px 0px'
});

animateElements.forEach((el, i) => {
  el.style.opacity = '0';
  el.style.transform = 'translateY(30px)';
  el.style.transition = `all 0.6s cubic-bezier(0.4, 0, 0.2, 1) ${i * 0.08}s`;
  observer.observe(el);
});

// ===== Console Greeting =====
console.log('%c📚 Attendance Management System', 'font-size: 20px; font-weight: bold; color: #3498DB;');
console.log('%cA Java Swing-based ERP for academic management', 'font-size: 14px; color: #2C3E50;');
console.log('%c🔗 https://github.com/ashudubeyvns/attendance-management-system', 'font-size: 12px; color: #7f8c8d;');
