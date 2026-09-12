import { useState } from "react";
import { Link } from "react-router-dom";

function Todos() {
  const [todos, setTodos] = useState([
    {
      id: 1,
      title: "Have breakfast",
      completed: false,
    },
    {
      id: 2,
      title: "Go for a short walk",
      completed: false,
    },
    {
      id: 3,
      title: "Call family",
      completed: false,
    },
  ]);

  function toggleTodo(id) {
    setTodos(
      todos.map((todo) =>
        todo.id === id
          ? { ...todo, completed: !todo.completed }
          : todo
      )
    );
  }

  return (
    <main className="todos-page">
      <Link to="/" className="back-home">
        ← Back to Home
      </Link>

      <h1>✅ To-Dos</h1>
      <p>Things you can do today</p>

      <section className="todo-list">
        {todos.map((todo) => (
          <div className="todo-item" key={todo.id}>
            <div>
              <h2>{todo.title}</h2>
            </div>

            <button onClick={() => toggleTodo(todo.id)}>
              {todo.completed ? "✓ Done" : "Mark as done"}
            </button>
          </div>
        ))}
      </section>
    </main>
  );
}

export default Todos;