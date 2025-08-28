import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';

const TaskForm = ({ onAdd, editingTask, onUpdate }) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');

  useEffect(() => {
    if (editingTask !== null) {
      setTitle(editingTask.title || '');
      setDescription(editingTask.description || '');
    } else {
      setTitle('');
      setDescription('');
    }
  }, [editingTask]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!title.trim()) return;
    if (editingTask !== null) {
      onUpdate({ title, description });
    } else {
      onAdd({ title, description });
    }
    setTitle('');
    setDescription('');
  };

  return (
    <form className="task-form" onSubmit={handleSubmit}>
      <input
        id="task-title"
        type="text"
        placeholder="Enter task title"
        value={title}
        onChange={e => setTitle(e.target.value)}
      />
      <input
        id="task-description"
        type="text"
        placeholder="Enter task description"
        value={description}
        onChange={e => setDescription(e.target.value)}
      />
      <button id="add-task-btn" type="submit">{editingTask ? 'Update' : 'Add'} Task</button>
    </form>
  );
};

TaskForm.propTypes = {
  onAdd: PropTypes.func.isRequired,
  editingTask: PropTypes.shape({
    title: PropTypes.string,
    description: PropTypes.string,
  }),
  onUpdate: PropTypes.func.isRequired,
};

export default TaskForm;
