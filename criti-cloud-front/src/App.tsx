import { useEffect, useState } from "react";

function App() {
  const [response, setResponse] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/test") // <- dopasuj endpoint
      .then((res) => {
        if (!res.ok) throw new Error(`Status: ${res.status}`);
        return res.json();
      })
      .then((data) => setResponse(data))
      .catch((err) => setResponse(err.message));
  }, []);

  console.log(response);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <pre>
        {response
          ? JSON.stringify(response, null, 2)
          : "Czekam na odpowiedź..."}
      </pre>
    </div>
  );
}

export default App;
