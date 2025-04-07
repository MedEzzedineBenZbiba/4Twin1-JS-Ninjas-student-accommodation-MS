import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import BlocPage from './pages/BlocPage';
import ChambrePage from './pages/ChambrePage';
import './App.css';

function App() {
    return (
        <Router>
            <nav style={{ marginBottom: '20px' }}>
                <Link to="/blocs" style={{ marginRight: '10px' }}>Blocs</Link>
                <Link to="/chambres">Chambres</Link>
            </nav>
            <Routes>
                <Route path="/blocs" element={<BlocPage />} />
                <Route path="/chambres" element={<ChambrePage />} />
            </Routes>
        </Router>
    );
}

export default App;