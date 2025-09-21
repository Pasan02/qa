import React, { useState, useEffect } from 'react';
import Auth from './components/Auth';
import TaskList from './components/TaskList';
import TaskForm from './components/TaskForm';
import './App.css';

function App() {
  // keep auth in-memory for now (backend auth not implemented)
  // userEmail for display, token for auth headers
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(null);
  const [tasks, setTasks] = useState([]);
  const [editingIdx, setEditingIdx] = useState(null);
  // backend base URL (matches backend `server.port` = 8081)
  const API_BASE = 'http://localhost:8081/api';

  useEffect(() => {
    // Fetch tasks from backend (backend now runs on 8081)
    console.debug('Fetching tasks from', `${API_BASE}/tasks`);
    const headers = {};
    if (token) headers['Authorization'] = `Bearer ${token}`;
    fetch(`${API_BASE}/tasks`, { headers })
      .then(res => res.json())
      .then(data => {
        console.debug('Received tasks', data);
        setTasks(data);
      })
      .catch(err => console.error('Failed to fetch tasks', err));
  }, [token]);

  // handleAuth(token, email)
  const handleAuth = (tokenValue, email) => {
    setToken(tokenValue);
    setUser(email);
  };

  const handleAddTask = async ({ title, description }) => {
    console.debug('handleAddTask', { title, description });
    try {
      const headers = { 'Content-Type': 'application/json' };
      if (token) headers['Authorization'] = `Bearer ${token}`;
      const response = await fetch(`${API_BASE}/tasks`, {
        method: 'POST',
        headers,
        body: JSON.stringify({ title, description }),
      });
      if (!response.ok) {
        const text = await response.text();
        console.error('Create task failed', response.status, text);
        return;
      }
      const newTask = await response.json();
      console.debug('Created task', newTask);
      setTasks([...tasks, newTask]);
    } catch (err) {
      console.error('Failed to create task', err);
    }
  };

  const handleDeleteTask = async (taskId) => {
    console.debug('handleDeleteTask', taskId);
    try {
      const headers = {};
      if (token) headers['Authorization'] = `Bearer ${token}`;
      const response = await fetch(`${API_BASE}/tasks/${taskId}`, {
        method: 'DELETE',
        headers,
      });
      if (!response.ok) {
        console.error('Delete failed', response.status);
        return;
      }
      setTasks(tasks.filter(task => task.id !== taskId));
      setEditingIdx(null);
    } catch (err) {
      console.error('Failed to delete task', err);
    }
  };

  const handleEditTask = (idx) => {
    setEditingIdx(idx);
  };

  const handleUpdateTask = async ({ title, description }) => {
    const task = tasks[editingIdx];
    console.debug('handleUpdateTask', { id: task?.id, title, description });
    try {
      const headers = { 'Content-Type': 'application/json' };
      if (token) headers['Authorization'] = `Bearer ${token}`;
      const response = await fetch(`${API_BASE}/tasks/${task.id}`, {
        method: 'PUT',
        headers,
        body: JSON.stringify({ title, description }),
      });
      if (!response.ok) {
        console.error('Update failed', response.status);
        return;
      }
      const updatedTask = await response.json();
      const newTasks = tasks.map((t, i) =>
        i === editingIdx ? updatedTask : t
      );
      setTasks(newTasks);
      setEditingIdx(null);
    } catch (err) {
      console.error('Failed to update task', err);
    }
  };

  const handleLogout = () => {
    setUser(null);
  };

  return (
    <>
      <div className="header">Task Tracker</div>
      <div className="center-wrapper">
        <div className="app-container">
          {!user ? (
            <Auth onAuth={handleAuth} />
          ) : (
            <>
              <div className="user-bar">
                <span>Welcome, {user}</span>
                <button onClick={handleLogout}>Logout</button>
              </div>
              <TaskForm
                onAdd={handleAddTask}
                editingTask={editingIdx !== null ? tasks[editingIdx] : null}
                onUpdate={handleUpdateTask}
              />
              <TaskList
                tasks={tasks}
                onDelete={handleDeleteTask}
                onEdit={handleEditTask}
              />
            </>
          )}
        </div>
      </div>
    </>
  );
}

export default App;
