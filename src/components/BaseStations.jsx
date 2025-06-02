import React, { useState } from 'react';

const baseStationsData = [
  { nodeId: '1001', networkId: '5001', name: 'Central Hub', status: 'Enabled' },
  { nodeId: '1002', networkId: '5001', name: 'North Tower', status: 'Enabled' },
  { nodeId: '1003', networkId: '5002', name: 'South Gateway', status: 'Disabled' },
  { nodeId: '1004', networkId: '5003', name: 'East Link', status: 'Enabled' },
  { nodeId: '1005', networkId: '5004', name: 'West Point', status: 'Disabled' },
  { nodeId: '1006', networkId: '5005', name: 'Metro Core', status: 'Enabled' },
];

const BaseStations = () => {
  const [search, setSearch] = useState('');
  const [page, setPage] = useState(1);
  const pageSize = 4;

  const filteredData = baseStationsData.filter(station =>
    station.name.toLowerCase().includes(search.toLowerCase())
  );

  const totalPages = Math.ceil(filteredData.length / pageSize);
  const currentPageData = filteredData.slice((page - 1) * pageSize, page * pageSize);

  const start = (page - 1) * pageSize + 1;
  const end = Math.min(start + currentPageData.length - 1, filteredData.length);

  return (
    <div className="card p-4 shadow-sm">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h4>📡 Configured Base Stations</h4>
        <input
          type="text"
          className="form-control"
          style={{ maxWidth: '260px' }}
          placeholder="Search by name..."
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
              <td>{station.name}</td>
              <td className="text-start">
                <span
                  className={`badge px-3 py-2 rounded-pill ${
                    station.status === 'Enabled' ? 'bg-success' : 'bg-secondary'
                  }`}
                >
                  {station.status}
                </span>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="d-flex justify-content-between align-items-center">
        <div className="text-muted">
          Showing {start}–{end} of {filteredData.length}
        </div>
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
