// ============================================================
// السكربت ده انسخه والصقه في كونسول المتصفح (F12 → Console)
// على الصفحة اللي عايز تجيب منها السيلكتورز، ثم اضغط Enter
// ============================================================

(function() {
  const out = [];
  const seen = new Set();

  document.querySelectorAll('[id],[data-testid],[name],[data-test-id]').forEach(el => {
    const tag = el.tagName.toLowerCase();
    if (el.id && el.id.trim()) {
      const k = 'id:' + el.id;
      if (!seen.has(k)) { seen.add(k); out.push({ type: 'id', value: el.id, tag, by: `By.id("${el.id}")` }); }
    }
    const testId = el.getAttribute('data-testid') || el.getAttribute('data-test-id');
    if (testId) {
      const k = 'testid:' + testId;
      if (!seen.has(k)) { seen.add(k); out.push({ type: 'data-testid', value: testId, tag, by: `By.cssSelector("[data-testid='${testId}']")` }); }
    }
    if (['input','select','textarea','button'].includes(tag) && el.name) {
      const k = 'name:' + el.name;
      if (!seen.has(k)) { seen.add(k); out.push({ type: 'name', value: el.name, tag, by: `By.name("${el.name}")` }); }
    }
  });

  console.table(out);
  console.log('\n--- جاهز للنسخ (Java Selenium By) ---');
  out.forEach(o => console.log(o.by + "  // " + o.tag + " [" + o.type + "]"));
  return out;
})();
