import { useState, useEffect } from "react";
import "./App.css";

const API_URL = "http://localhost:8080/api/notes";

function App() {
  const [notes, setNotes] = useState([]);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const fetchNotes = async () => {
    const res = await fetch(API_URL);
    const data = await res.json();
    setNotes(data);
  };

  useEffect(() => {
    fetchNotes();
  }, []);

  const addNote = async (e) => {
    e.preventDefault();
    if (!title.trim()) return;
    await fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ title, content }),
    });
    setTitle("");
    setContent("");
    fetchNotes();
  };

  const markDone = async (id) => {
    await fetch(`${API_URL}/${id}/done`, { method: "PATCH" });
    fetchNotes();
  };

  const deleteNote = async (id) => {
    await fetch(`${API_URL}/${id}`, { method: "DELETE" });
    fetchNotes();
  };

  return (
    <div className="container">
      <h1>QuickNotes</h1>

      <form onSubmit={addNote} className="note-form">
        <input
          type="text"
          placeholder="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <textarea
          placeholder="Content"
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
        <button type="submit">Add Note</button>
      </form>

      <div className="note-list">
        {notes.length === 0 && <p>No notes yet.</p>}
        {notes.map((note) => (
          <div key={note.id} className={`note-card ${note.status === "DONE" ? "done" : ""}`}>
            <h3>{note.title}</h3>
            <p>{note.content}</p>
            <span className="status">{note.status}</span>
            <div className="actions">
              {note.status !== "DONE" && (
                <button onClick={() => markDone(note.id)}>Mark Done</button>
              )}
              <button onClick={() => deleteNote(note.id)} className="delete">
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;