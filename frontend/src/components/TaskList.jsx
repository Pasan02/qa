import React from 'react';

function TaskList({ tasks, onDelete, onEdit }) {
  return (
    <div className="task-list">
      <h2>Tasks</h2>
      {tasks.length === 0 ? (
        <p>No tasks yet.</p>
      ) : (
        <ul id="task-list">
          {tasks.map((task, idx) => (
            <li key={task.id} id={`task-item-${idx}`}>
              <span>
                <strong>{task.title}</strong>: {task.description}
              </span>
              <button id={`edit-task-btn-${idx}`} onClick={() => onEdit(idx)}>Edit</button>
              <button id={`delete-task-btn-${idx}`} onClick={() => onDelete(task.id)}>Delete</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default TaskList;
