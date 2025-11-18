<script>
document.querySelectorAll('.tooltip').forEach(t => {
  const card = t.querySelector('.tooltip-card');
  if (!card) return;

  t.addEventListener('mouseenter', () => {
    card.style.right = ''; card.style.left = '0';
    requestAnimationFrame(() => {
      const r = card.getBoundingClientRect();
      if (r.right > window.innerWidth - 12) {
        card.style.left = 'auto';
        card.style.right = '0';
        card.style.setProperty('--arrow-x', 'auto');
      }
    });
  });
});
</script>
