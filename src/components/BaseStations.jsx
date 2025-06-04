import React, { useEffect, useState } from 'react';
import axios from 'axios';

const BaseStations = () => {
  const [stations, setStations] = useState([]);
  const [search, setSearch] = useState('');
  const [page, setPage] = useState(1);
  const pageSize = 4;

  const fetchStations = async () => {
    try {
      const res = await axios.get('http://localhost:8081/api/base-stations');
      setStations(res.data);
    } catch (err) {
      console.error('Error fetching stations', err);
    }
  };

  useEffect(() => {
    fetchStations();
  }, []);

  const toggleStation = async (nodeId, currentStatus) => {
    try {
      await axios.put(`http://localhost:9092/api/base-stations/${nodeId}`, {
        enabled: !currentStatus,
      });
      fetchStations();
    } catch (err) {
      console.error('Error toggling station', err);
    }
  };

  const filtered = stations.filter(s => s.networkName.toLowerCase().includes(search.toLowerCase()));
  const totalPages = Math.ceil(filtered.length / pageSize);
  const currentPageData = filtered.slice((page - 1) * pageSize, page * pageSize);

  return (
    <div className="card p-4 shadow-sm">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h4>📡 Configured Base Stations</h4>
        <input
          type="text"
          className="form-control w-25"
          placeholder="Search by network name..."
          value={search}
          onChange={(e) => {
            setSearch(e.target.value);
            setPage(1);
          }}
        />
      </div>

      <table className="table table-hover table-bordered align-middle">
        <thead className="table-light">
          <tr>
            <th>Node ID</th>
            <th>Network ID</th>
            <th>Network Name</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {currentPageData.map((station, idx) => (
            <tr key={idx}>
              <td>{station.nodeId}</td>
              <td>{station.networkId}</td>
              <td>{station.networkName}</td>
              <td>
                <div className="form-check form-switch">
                  <input
                    className="form-check-input"
                    type="checkbox"
                    checked={station.enabled}
                    onChange={() => toggleStation(station.nodeId, station.enabled)}
                  />
                  <label className="form-check-label">
                    {station.enabled ? 'Enabled' : 'Disabled'}
                  </label>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="d-flex justify-content-between align-items-center">
        <span className="text-muted">Showing page {page} of {totalPages}</span>
        <div>
          <button
            className="btn btn-outline-primary btn-sm me-2"
            disabled={page === 1}
            onClick={() => setPage(page - 1)}
          >
            ◀ Previous
          </button>
          <button
            className="btn btn-outline-primary btn-sm"
            disabled={page === totalPages}
            onClick={() => setPage(page + 1)}
          >
            Next ▶
          </button>
        </div>
      </div>
    </div>
  );
};

export default BaseStations;
