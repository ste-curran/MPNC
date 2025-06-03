import React, { useState } from 'react';
import axios from 'axios';

const AddStation = () => {
  const [form, setForm] = useState({
    nodeId: '',
    networkId: '',
    networkName: '',
    enabled: false
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:9092/api/base-stations', form);
      alert('Radio station added successfully!');
      setForm({ nodeId: '', networkId: '', networkName: '', enabled: false });
    } catch (error) {
      alert('Error adding radio station. Please try again.');
      console.error(error);
    }
  };

  return (
    <div className="card p-4 shadow-sm">
      <h4 className="mb-4">➕ Add Radio Station</h4>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Node ID *</label>
          <input type="number" className="form-control" name="nodeId" value={form.nodeId} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label className="form-label">Network ID *</label>
          <input type="number" className="form-control" name="networkId" value={form.networkId} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label className="form-label">Network Name *</label>
          <input type="text" className="form-control" name="networkName" value={form.networkName} onChange={handleChange} required />
        </div>
        <div className="form-check form-switch mb-3">
          <input
            className="form-check-input"
            type="checkbox"
            name="enabled"
            checked={form.enabled}
            onChange={handleChange}
          />
          <label className="form-check-label">Enable Data Streaming</label>
        </div>
        <button type="submit" className="btn btn-primary w-100">Add Station</button>
      </form>
    </div>
  );
};

export default AddStation;
