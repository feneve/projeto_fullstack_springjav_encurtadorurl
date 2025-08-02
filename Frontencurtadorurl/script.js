async function encurtarUrl() {
  const longUrl = document.getElementById('longUrl').value;
  const expiration = document.getElementById('expiration').value;

  const body = {
    longUrl: longUrl,
    expiration: expiration ? expiration : null
  };

  try {
    const response = await fetch('http://localhost:8080/api/short', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(body)
    });

    if (response.ok) {
      const data = await response.json();
      document.getElementById('shortUrl').value = data.shortUrl;
      document.getElementById('resultado').style.display = 'block';
    } else {
      alert("Erro ao encurtar URL");
    }
  } catch (error) {
    console.error(error);
    alert("Erro ao conectar com o servidor");
  }
}

function copiarUrl() {
  const copyText = document.getElementById("shortUrl");
  copyText.select();
  copyText.setSelectionRange(0, 99999);
  document.execCommand("copy");
  alert("URL copiada: " + copyText.value);
}
