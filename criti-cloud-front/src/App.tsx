import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Movies from "./pages/Movies";

function App() {
  return (
    <Router>
      <nav>
        <Link to="/">Movies</Link>
        {/* | <Link to="/about">About</Link> */}
      </nav>

      <Routes>
        <Route path="/" element={<Movies />} />
        {/* <Route path="/about" element={<About />} /> */}
      </Routes>
    </Router>
  );
}

export default App;
