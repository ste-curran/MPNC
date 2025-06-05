import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { FaServer, FaDatabase, FaClock, FaBroadcastTower } from 'react-icons/fa';

const Dashboard = () => {
  const [stats, setStats] = useState({
    kafka: 'Connected',
    database: 'Connected',
    calls: 0,
    activeStations: 0
  });

  const [stations, setStations] = useState([]);

  const fetchDashboardData = async () => {
    try {
      const callsRes = await axios.get('/api/data/total-calls');
      const stationsRes = await axios.get('/api/base-stations');
      
      setStats(prev => ({
        ...prev,
        calls: callsRes.data.totalCalls,
        activeStations: stationsRes.data.filter(s => s.enabled).length
      }));
      setStations(stationsRes.data);
    } catch (error) {
      console.error('Error fetching dashboard data:', error);
    }
  };

  const toggleStatus = async (station) => {
    try {
      await axios.put(`/api/base-stations/${station.nodeId}/status`, {
        enabled: !station.enabled
      });
      fetchDashboardData();
    } catch (error) {
      console.error('Error toggling status:', error);
    }
  };

  useEffect(() => {
    fetchDashboardData();
    const interval = setInterval(fetchDashboardData, 60000);
    return () => clearInterval(interval);
  }, []);

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
          {stations.map(station => (
            <div key={station.nodeId} className="d-flex justify-content-between align-items-center border rounded p-3">
              <div>
                <strong>{station.networkName}</strong><br />
                <small>Node: {station.nodeId} | Network: {station.networkId}</small>
              </div>
              <button
                className={`btn btn-sm px-3 ${station.enabled ? 'btn-success' : 'btn-secondary'}`}
                onClick={() => toggleStatus(station)}
              >
                {station.enabled ? 'Streaming' : 'Disabled'}
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
