import React, { useState } from 'react';
import Auth from './components/Auth';
import TaskList from './components/TaskList';
import TaskForm from './components/TaskForm';
import './App.css';

function App() {
  const [user, setUser] = useState(localStorage.getItem('user') || null);
  const [tasks, setTasks] = useState(() => {
    const saved = localStorage.getItem('tasks');
    return saved ? JSON.parse(saved) : [];
  });
  const [editingIdx, setEditingIdx] = useState(null);

  const handleAuth = (email) => {
    setUser(email);
  };

  const handleAddTask = ({ title, description }) => {
    const newTasks = [...tasks, { title, description }];
    setTasks(newTasks);
    localStorage.setItem('tasks', JSON.stringify(newTasks));
  };

  const handleDeleteTask = (idx) => {
    const newTasks = tasks.filter((_, i) => i !== idx);
    setTasks(newTasks);
    localStorage.setItem('tasks', JSON.stringify(newTasks));
    setEditingIdx(null);
  };

  const handleEditTask = (idx) => {
    setEditingIdx(idx);
  };

  const handleUpdateTask = ({ title, description }) => {
    const newTasks = tasks.map((task, i) =>
      i === editingIdx ? { title, description } : task
    );
    setTasks(newTasks);
    localStorage.setItem('tasks', JSON.stringify(newTasks));
    setEditingIdx(null);
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('user');
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
