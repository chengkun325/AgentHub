import hljs from "highlight.js";
import MarkdownIt from "markdown-it";




const md:MarkdownIt  = MarkdownIt({
  highlight: function (str, lang) {
    if (lang && hljs.getLanguage(lang)) {
      try {
        return `<pre class="hljs" style="overflow-x: auto;"><code>${hljs.highlight(lang, str, true).value}</code></pre>`;
      } catch (__) {
        //
      }
    }

    return `<pre class="hljs" style="overflow-x: auto;" ><code>${md.utils.escapeHtml(str)}</code></pre>`;
  },
});



const mdi = new MarkdownIt({
  html: true,
  linkify: true,
  highlight(code, language) {
    const validLang = !!(language && hljs.getLanguage(language))
    if (validLang) {
      const lang = language ?? ''
      return highlightBlock(hljs.highlight(code, { language: lang }).value, lang)
    }
    return highlightBlock(hljs.highlightAuto(code).value, '')
  },
})
function highlightBlock(str: string, lang?: string) {
  return `<pre class="code-pre"><code class="hljs ${lang}">${str}</code></pre>`
}













export default mdi;