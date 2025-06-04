import React, { useState, useEffect } from 'react';
import axios from 'axios';

const CallData = () => {
  const [callData, setCallData] = useState([]);
  const [search, setSearch] = useState('');
  const [page, setPage] = useState(1);
  const pageSize = 4;

  useEffect(() => {
    fetchCallData();
  }, []);

  const fetchCallData = async () => {
    try {
      const res = await axios.get('http://localhost:9094/api/call-data');
      setCallData(res.data);
    } catch (error) {
      console.error('Error fetching call data:', error);
    }
  };

  const filteredData = callData.filter(entry =>
    entry.networkName.toLowerCase().includes(search.toLowerCase())
  );

  const totalCalls = filteredData.reduce((sum, entry) => sum + entry.callsHandled, 0);
  const totalPages = Math.ceil(filteredData.length / pageSize);
  const currentPageData = filteredData.slice((page - 1) * pageSize, page * pageSize);

  return (
    <div className="card p-4 shadow-sm">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h4>📞 Call Data</h4>
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

      <div className="mb-3">
        <h6 className="text-primary">
          Total Calls (Last 60 seconds): <strong>{totalCalls}</strong>
        </h6>
      </div>

      <table className="table table-bordered table-striped align-middle">
        <thead className="table-light">
          <tr>
            <th>Node ID</th>
            <th>Network ID</th>
            <th>Network Name</th>
            <th>Calls (60s)</th>
            <th>Period</th>
          </tr>
        </thead>
        <tbody>
          {currentPageData.map((entry, idx) => (
            <tr key={idx}>
              <td>{entry.nodeId}</td>
              <td>{entry.networkId}</td>
              <td>{entry.networkName}</td>
              <td><strong>{entry.callsHandled}</strong></td>
              <td>{entry.mobileCallPeriod.replace('T', ' ')}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="d-flex justify-content-between">
        <button
          className="btn btn-outline-primary btn-sm"
          disabled={page === 1}
          onClick={() => setPage(page - 1)}
        >
          ◀ Previous
        </button>
        <span className="text-muted">Page {page} of {totalPages}</span>
        <button
          className="btn btn-outline-primary btn-sm"
          disabled={page === totalPages}
          onClick={() => setPage(page + 1)}
        >
          Next ▶
        </button>
      </div>
    </div>
  );
};

export default CallData;
