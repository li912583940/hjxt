<script>
export default {
  name: 'AuthRedirect',
  created() {
    const hash = window.location.search.slice(1)
    console.log(8888888888888)
    console.log(hash)
    window.opener.location.href = window.location.origin + '/login#' + hash
    window.close()
  }
}
</script>
