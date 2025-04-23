// src/App.jsx
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import UniversiteStats from "./components/UniversiteStats";
import Login from "./components/Login";
import ProtectedRoute from "./components/ProtectedRoute";
import StudentList from "./components/StudentList";
import Reservation from "./components/Reservation";
import Foyer from "./components/Foyer";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route
          path="/universite"
          element={
            <ProtectedRoute>
              <UniversiteStats />
            </ProtectedRoute>
          }
        />
         <Route
          path="/student"
          element={
            <ProtectedRoute>
              <StudentList/>
            </ProtectedRoute>
          }
        />
          <Route
          path="/reservation"
          element={
            <ProtectedRoute>
              <Reservation/>
            </ProtectedRoute>
          }
        />
          <Route
          path="/foyer"
          element={
            <ProtectedRoute>
              <Foyer/>
            </ProtectedRoute>
          }
        />
        
      
        <Route path="*" element={<Login />} />
      </Routes>
    </Router>
  );
}

export default App;
