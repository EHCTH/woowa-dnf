console.log('[tabs] external script loaded');

window.openTab = function(evt, tabId) {
  console.log("[openTab]", tabId);
  const contents = document.querySelectorAll('.tab-content');
  const buttons  = document.querySelectorAll('.tab-button');

  contents.forEach(c => c.classList.remove('active'));
  buttons.forEach(b => b.classList.remove('active'));

  const panel = document.getElementById(tabId);
  if (panel) panel.classList.add('active');
  if (evt && evt.currentTarget) evt.currentTarget.classList.add('active');
};
