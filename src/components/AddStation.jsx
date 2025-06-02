import React, { useState } from 'react';

const AddStation = () => {
  const [form, setForm] = useState({
    nodeId: '',
    networkId: '',
    name: '',
    streaming: false
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert(`Station added:\n${JSON.stringify(form, null, 2)}`);
    setForm({ nodeId: '', networkId: '', name: '', streaming: false });
  };

  return (
    <div className="card p-4 shadow-sm">
      <h4 className="mb-4">➕ Add Radio Station</h4>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Node ID *</label>
          <input
            type="text"
            className="form-control"
            name="nodeId"
            value={form.nodeId}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Network ID *</label>
          <input
            type="text"
            className="form-control"
            name="networkId"
            value={form.networkId}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Network Name *</label>
          <input
            type="text"
            className="form-control"
            name="name"
            value={form.name}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-check form-switch mb-4">
          <input
            className="form-check-input"
            type="checkbox"
            name="streaming"
            checked={form.streaming}
            onChange={handleChange}
          />
          <label className="form-check-label">Enable Data Streaming</label>
        </div>
        <div className="text-end">
          <button type="submit" className="btn btn-primary px-4">
            Add Station
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddStation;
