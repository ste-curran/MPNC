import React from 'react';
import { NavLink } from 'react-router-dom';
import './Sidebar.css'; 

const Sidebar = () => {
  return (
    <div className="sidebar d-flex flex-column">
      <div className="sidebar-header mb-3 px-3 py-2">
        <h5>🌐 Network Config</h5>
      </div>

      <div className="sidebar-section px-3">
        <h6 className="section-title">Network Functions</h6>
        <NavLink to="/" end className="sidebar-link">
          📊 Dashboard
        </NavLink>
        <NavLink to="/add" className="sidebar-link">
          👤 Add Station
        </NavLink>
        <NavLink to="/stations" className="sidebar-link">
          🛰️ Base Stations
        </NavLink>
        <NavLink to="/calls" className="sidebar-link">
          📞 Call Data
        </NavLink>
      </div>
    </div>
  );
};

export default Sidebar;
