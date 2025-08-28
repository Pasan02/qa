import React from 'react';
import PropTypes from 'prop-types';

const TaskList = ({ tasks, onDelete, onEdit }) => (
  <div className="task-list">
    <h2>Tasks</h2>
    {tasks.length === 0 ? (
      <p>No tasks yet.</p>
    ) : (
      <ul id="task-list">
        {tasks.map((task, idx) => (
          <li key={idx} id={`task-item-${idx}`}>
            <span>
              <strong>{task.title}</strong>: {task.description}
            </span>
            <button id={`edit-task-btn-${idx}`} onClick={() => onEdit(idx)}>Edit</button>
            <button id={`delete-task-btn-${idx}`} onClick={() => onDelete(idx)}>Delete</button>
          </li>
        ))}
      </ul>
    )}
  </div>
);

TaskList.propTypes = {
  tasks: PropTypes.arrayOf(
    PropTypes.shape({
      title: PropTypes.string.isRequired,
      description: PropTypes.string,
    })
  ).isRequired,
  onDelete: PropTypes.func.isRequired,
  onEdit: PropTypes.func.isRequired,
};

export default TaskList;
