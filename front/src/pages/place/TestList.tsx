import React, { useState } from "react";
import TestItem from "./TestItem";
import { DragDropContext, Draggable, Droppable } from "react-beautiful-dnd";

export default function TestList({ items }: any) {
  const [item, setItems] = useState(items);
  const onDragEnd = (result: any) => {
    const { destination, source, draggableId } = result;
    if (!destination) {
      return;
    }
    if (
      destination.droppableId === source.droppableId &&
      destination.index === source.index
    ) {
      return;
    }
  };

  return (
    <DragDropContext onDragEnd={onDragEnd}>
      <Droppable droppableId="cardlists">
        {(provided) => (
          <div
            id="cardLists"
            className="cardlists p-[10px] border-[2px]"
            {...provided.droppableProps}
            ref={provided.innerRef}
          >
            {item.map((e: any, i: number) => (
              <Draggable draggableId={e.sidoCode} index={i} key={e.sidoCode}>
                {(provided, snapshot) => {
                  return (
                    <div
                      className="p-[8px] m-[8px] border-[4px] rounded-lg bg-[#ffffff]"
                      {...provided.draggableProps}
                      {...provided.dragHandleProps}
                      ref={provided.innerRef}
                    >
                      <TestItem sidoName={e.sidoName} />
                    </div>
                  );
                }}
              </Draggable>
            ))}
          </div>
        )}
      </Droppable>
    </DragDropContext>
  );
}
