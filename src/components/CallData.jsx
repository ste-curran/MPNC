import React, { useState } from 'react';

const callData = [
  { nodeId: '1001', networkId: '5001', name: 'Central Hub', calls: 234, time: '2025-06-02 10:30:00' },
  { nodeId: '1002', networkId: '5001', name: 'North Tower', calls: 189, time: '2025-06-02 10:30:00' },
  { nodeId: '1003', networkId: '5002', name: 'South Gateway', calls: 0, time: '2025-06-02 10:30:00' },
  { nodeId: '1004', networkId: '5001', name: 'East Link', calls: 156, time: '2025-06-02 10:30:00' },
];

const CallData = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [page, setPage] = useState(0);
  const rowsPerPage = 3;

  const filteredData = callData.filter(entry =>
    entry.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const totalCalls = filteredData.reduce((sum, entry) => sum + entry.calls, 0);
  const pageCount = Math.ceil(filteredData.length / rowsPerPage);
  const paginated = filteredData.slice(page * rowsPerPage, (page + 1) * rowsPerPage);

  return (
    <div className="card p-4 shadow-sm">
      <h4 className="mb-3">📞 Call Data</h4>
      <h5 className="text-primary mb-4">Total Calls (Last 60 seconds): <strong>{totalCalls}</strong></h5>

      <div className="d-flex justify-content-end mb-3">
        <input
          type="text"
          className="form-control w-25"
          placeholder="Search by Network Name..."
          value={searchTerm}
          onChange={(e) => {
            setSearchTerm(e.target.value);
            setPage(0); // Reset to first page on search
          }}
        />
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
          {paginated.map((call, idx) => (
            <tr key={idx}>
              <td>{call.nodeId}</td>
              <td>{call.networkId}</td>
              <td>{call.name}</td>
              <td><strong>{call.calls}</strong></td>
              <td>{call.time}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="d-flex justify-content-between align-items-center mt-3">
        <small>Showing {page * rowsPerPage + 1}–{Math.min((page + 1) * rowsPerPage, filteredData.length)} of {filteredData.length}</small>
        <div>
          <button
            className="btn btn-outline-primary btn-sm me-2"
            onClick={() => setPage(p => Math.max(p - 1, 0))}
            disabled={page === 0}
          >
            ◀ Previous
          </button>
          <button
            className="btn btn-outline-primary btn-sm"
            onClick={() => setPage(p => Math.min(p + 1, pageCount - 1))}
            disabled={page >= pageCount - 1}
          >
            Next ▶
          </button>
        </div>
      </div>
    </div>
  );
};

export default CallData;
