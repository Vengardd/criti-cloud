import { useEffect, useState } from "react";

function App() {
  const [response, setResponse] = useState<string>("");

  useEffect(() => {
    fetch("http://localhost:8080/test")
      .then((res) => res.text())
      .then((text) => setResponse(text))
      .catch((err) => console.error(err));
  }, []);

  console.log(response);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <pre>{response ? response : "Czekam na odpowiedź..."}</pre>
    </div>
  );
}

export default App;
