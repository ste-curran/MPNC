import React from 'react';
import { FaServer, FaDatabase, FaClock, FaBroadcastTower } from 'react-icons/fa';

const Dashboard = () => {
  const stats = {
    kafka: 'Connected',
    database: 'Connected',
    calls: 1847,
    activeStations: 3
  };

  return (
    <div className="dashboard">
      <h4 className="mb-4">📡 Mobile Network Configuration</h4>
      <p className="text-muted mb-4">Manage radio base stations and monitor network performance</p>

      <div className="row mb-5">
        <div className="col-md-3">
          <div className="card p-3 shadow-sm text-center">
            <FaServer className="text-success fs-3 mb-2" />
            <h6>Kafka Status</h6>
            <strong>{stats.kafka}</strong>
          </div>
        </div>
        <div className="col-md-3">
          <div className="card p-3 shadow-sm text-center">
            <FaDatabase className="text-primary fs-3 mb-2" />
            <h6>Database Status</h6>
            <strong>{stats.database}</strong>
          </div>
        </div>
        <div className="col-md-3">
          <div className="card p-3 shadow-sm text-center">
            <FaClock className="text-warning fs-3 mb-2" />
            <h6>Calls (Last 60 min)</h6>
            <strong>{stats.calls.toLocaleString()}</strong>
          </div>
        </div>
        <div className="col-md-3">
          <div className="card p-3 shadow-sm text-center">
            <FaBroadcastTower className="text-info fs-3 mb-2" />
            <h6>Active Stations</h6>
            <strong>{stats.activeStations}</strong>
          </div>
        </div>
      </div>

      <div className="card p-4 shadow-sm">
        <h5 className="mb-3">Base Stations Status</h5>
        <div className="d-flex flex-column gap-3">
          <div className="d-flex justify-content-between align-items-center border rounded p-3">
            <div>
              <strong>Central Hub</strong><br />
              <small>Node: 1001 | Network: 5001</small>
            </div>
            <span className="badge bg-success px-3 py-2">Streaming</span>
          </div>

          <div className="d-flex justify-content-between align-items-center border rounded p-3">
            <div>
              <strong>North Tower</strong><br />
              <small>Node: 1002 | Network: 5001</small>
            </div>
            <span className="badge bg-success px-3 py-2">Streaming</span>
          </div>

          <div className="d-flex justify-content-between align-items-center border rounded p-3">
            <div>
              <strong>South Gateway</strong><br />
              <small>Node: 1003 | Network: 5002</small>
            </div>
            <span className="badge bg-secondary px-3 py-2">Disabled</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
