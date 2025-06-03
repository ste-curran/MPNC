import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Dashboard from './components/Dashboard';
import AddStation from './components/AddStation';
import BaseStations from './components/BaseStations';
import CallData from './components/CallData';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import { FaServer, FaDatabase, FaClock, FaBroadcastTower } from 'react-icons/fa';

const App = () => {
  return (
    <Router>
      <div className="d-flex" style={{ height: '100vh' }}>
        <Sidebar />
        <main className="flex-fill p-4 bg-light overflow-auto">
          <Routes>
            <Route path="/" element={<Dashboard icons={{ FaServer, FaDatabase, FaClock, FaBroadcastTower }} />} />
            <Route path="/add" element={<AddStation />} />
            <Route path="/stations" element={<BaseStations />} />
            <Route path="/calls" element={<CallData />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
};

export default App;
